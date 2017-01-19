"""
 Copyright 2015 ThreatConnect, Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 =============================================================================
"""

""" standard """
import os
import sys

""" third party """

""" custom """
from tcex import TcEx
import tcex.tcex_resources as Resources

tcex = TcEx()

#
# parse args
#
parser = tcex.parser

# parse
args = tcex.args

def main():
    """ """
    # logging examples
    tcex.log.debug('logging debug')
    tcex.log.info('logging info')
    tcex.log.error('logging error')
    tcex.log.critical('logging critical')

    # Get instance of Owner class
    resource = getattr(Resources, 'Owner')(tcex)

    # Add URL passed from TC
    resource.url = args.tc_api_path

    # Retrieve Results
    results = resource.request()

    # Check results status
    if results['status'] == 'Success':
        # Write Message TC
        tcex.message_tc(results['data'])
    else:
        # Write Message TC
        tcex.message_tc('Owner request failed')
        tcex.exit(1)

    # Exit
    tcex.exit()


if __name__ == "__main__":
    main()