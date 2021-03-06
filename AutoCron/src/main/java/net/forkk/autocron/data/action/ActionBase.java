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

package net.forkk.autocron.data.action;

import android.preference.PreferenceFragment;

import net.forkk.autocron.R;
import net.forkk.autocron.data.Automation;
import net.forkk.autocron.data.AutomationComponentBase;
import net.forkk.autocron.data.AutomationService;
import net.forkk.autocron.data.ComponentPointer;
import net.forkk.autocron.data.State;


/**
 * Abstract base class for actions that implements common functionality.
 */
public abstract class ActionBase extends AutomationComponentBase implements Action
{
    public ActionBase(Automation parent, AutomationService service, int id)
    {
        super(parent, service, id);
    }

    @Override
    public void create()
    {
        super.create();
        if (getParent().isEnabled() && isEnabled())
        {
            Automation parent = getParent();
            if (parent instanceof State)
            {
                State state = (State) parent;
                if (state.isActive()) onActivate();
                else onDeactivate();
            }
        }
    }

    @Override
    protected String getSharedPreferencesName(int id)
    {
        return "action_" + id;
    }

    protected static String getSharedPreferencesNameForId(int id)
    {
        return "action_" + id;
    }

    @Override
    public void addPreferencesToFragment(PreferenceFragment fragment)
    {
        super.addPreferencesToFragment(fragment);
        fragment.addPreferencesFromResource(R.xml.prefs_action);
    }

    @Override
    public ComponentPointer getPointer()
    {
        return new Pointer(this);
    }
}
