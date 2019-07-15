import logging
import subprocess
import os

logging.getLogger(__name__).addHandler(logging.NullHandler())


class XCRun(object):
    binary = 'xcrun'
    logger = logging.getLogger()
    
    def launch(self):
        # First close sim if any then launch
        os.system('~/Library/Android/sdk/platform-tools/adb devices | grep emulator | cut -f1 | while read line; do ~/Library/Android/sdk/platform-tools/adb -s $line emu kill; done')
        #os.system('emulator -avd Pixel_API_28 -wipe-data -no-boot-anim -screen no-touch & EMULATOR_PID=$! &')
        # Then launch sim
        os.system("sh launchSimScript.sh")
