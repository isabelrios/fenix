import logging
import os
import subprocess

here = os.path.dirname(__file__)
logging.getLogger(__name__).addHandler(logging.NullHandler())


class GradlewBuild(object):
    #os.chdir('/Users/isabelrios/git/fenix/')
    binary = 'gradlew'
    
    def __init__(self, log):
        self.log = log

    def test(self, identifier):
        self.logger.info('Running: ')
        args = './gradlew ' + 'app:connectedX86DebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=org.mozilla.fenix.ui.SyncIntegrationTests.SyncIntegrationTest#'.format(identifier)
        