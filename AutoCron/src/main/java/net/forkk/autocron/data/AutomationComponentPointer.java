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

package net.forkk.autocron.data;

import java.io.Serializable;


/**
 * Pointer to an automation component like a rule or action.
 */
public abstract class AutomationComponentPointer implements ComponentPointer, Serializable
{
    protected int mAutomationId;

    protected AutomationType mAutomationType;

    public AutomationComponentPointer(Automation automation)
    {
        mAutomationId = automation.getId();
        mAutomationType = automation instanceof State ? AutomationType.State : AutomationType.Event;
    }

    public AutomationComponentPointer(int automationId, AutomationType automationType)
    {
        mAutomationId = automationId;
        mAutomationType = automationType;
    }

    public Automation getAutomation(AutomationService.LocalBinder binder)
    {
        switch (mAutomationType)
        {
        case State:
            return binder.findStateById(mAutomationId);

        case Event:
            return binder.findEventById(mAutomationId);

        default:
            return null;
        }
    }

    public enum AutomationType
    {
        State,
        Event,
    }
}
