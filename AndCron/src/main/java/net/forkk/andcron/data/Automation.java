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

package net.forkk.andcron.data;

import net.forkk.andcron.data.action.Action;
import net.forkk.andcron.data.rule.Rule;


/**
 * Interface for automations. Provides access to all of the necessary stuff.
 */
public interface Automation extends ConfigComponent
{
    /**
     * @return An array of this automation's rules.
     */
    public Rule[] getRules();

    /**
     * @return An array of this automation's actions.
     */
    public Action[] getActions();

    /**
     * @return True or false depending on whether or not the automation is active.
     */
    public boolean isActive();

    /**
     * Called when a rule is activated or deactivated. This should recheck all of the rules and see
     * if the automation should be activated.
     */
    public void updateActivationState();

    /**
     * @return The automation service that this automation is attached to.
     */
    public AutomationService getService();
}