/*
 * Copyright 2013 Andrew Okin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.forkk.andcron.data.rule;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import net.forkk.andcron.R;
import net.forkk.andcron.data.Automation;
import net.forkk.andcron.data.AutomationService;


/**
 * A simple test rule that activates when
 */
public class TestRule extends RuleBase implements AutomationService.IntentListener
{
    public static final String NOTIFICATION_TAG = "net.forkk.andcron.testrule";

    private Notification mNotification;

    private int mIntentListenerId;

    public TestRule(Automation parent, Context context, int id)
    {
        super(parent, context, id);
    }

    /**
     * Called after the automation service finishes loading components. This should perform all
     * necessary initialization for this component.
     *
     * @param service
     *         Context to initialize with.
     */
    @Override
    public void onCreate(AutomationService service)
    {
        updateNotification(service);
        mIntentListenerId = service.registerIntentListener(this);
    }

    /**
     * Called when the automation service is destroyed. This should perform all necessary cleanup.
     */
    @Override
    public void onDestroy(AutomationService service)
    {
        NotificationManager notificationManager =
                (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_TAG, getId());
        service.unregisterIntentListener(mIntentListenerId);
    }

    public void updateNotification(AutomationService service)
    {
        updateNotification(service,
                           service.getString(R.string.notification_title_test_rule, getName()),
                           service.getString(R.string.notification_text_test_rule, getName(),
                                             isActive() ? "deactivate" : "activate"));
    }

    public void updateNotification(AutomationService service, String title, String text)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(service);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(R.drawable.ic_notification_icon);

        Intent notificationIntent = new Intent(service, AutomationService.class);
        notificationIntent.putExtra(AutomationService.LISTENER_ID_EXTRA, mIntentListenerId);
        PendingIntent pendingIntent = PendingIntent.getService(service, 0, notificationIntent,
                                                               PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);

        mNotification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_TAG, getId(), mNotification);
    }

    @Override
    public void setActive(boolean active)
    {
        super.setActive(active);
        updateNotification(getParent().getService());
    }

    @Override
    public void onCommandReceived(Intent intent)
    {
        setActive(!isActive());
    }
}
