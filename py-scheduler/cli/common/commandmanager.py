#   Copyright 2012-2013 OpenStack Foundation
#
#   Licensed under the Apache License, Version 2.0 (the "License"); you may
#   not use this file except in compliance with the License. You may obtain
#   a copy of the License at
#
#        http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
#   WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
#   License for the specific language governing permissions and limitations
#   under the License.
#

"""Modify cliff.CommandManager"""

import pkg_resources
import cliff.commandmanager


class CommandManager(cliff.commandmanager.CommandManager):
    """Add additional functionality to cliff.CommandManager

    Load additional command groups after initialization
    Add _command_group() methods
    """

    def __init__(self, namespace, convert_underscores=True):
        self.group_list = []
        super(CommandManager, self).__init__(namespace, convert_underscores)

    def load_commands(self, namespace):
        self.group_list.append(namespace)
        return super(CommandManager, self).load_commands(namespace)

    def add_command_group(self, group=None):
        """Adds another group of command entrypoints"""
        if group:
            self.load_commands(group)

    def get_command_groups(self):
        """Returns a list of the loaded command groups"""
        return self.group_list

    def add_all_command_group(self):
        pass
        # for ep in pkg_resources:
        #     print(ep)

    def get_command_names(self, group=None):
        """Returns a list of commands loaded for the specified group"""
        group_list = []
        if group is not None:
            for ep in pkg_resources.iter_entry_points(group):
                cmd_name = (
                    ep.name.replace('_', ' ')
                    if self.convert_underscores
                    else ep.name
                )
                group_list.append(cmd_name)
            return group_list
        return list(self.commands.keys())
