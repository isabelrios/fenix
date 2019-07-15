import os
import sys

def test_sync_history_from_desktop(tps, xcodebuild):
    # Old way running tests from script - cons how to select specific test
    #os.system("sh scriptName.sh")

    # Does not work changing files name???
    #gradlew.test()
    tps.run('test_history.js')
    xcodebuild.test('checkHistoryTest')

    # For the future, this way we change the test to run....
    #xcodebuild.test('getHistoryTest')
    