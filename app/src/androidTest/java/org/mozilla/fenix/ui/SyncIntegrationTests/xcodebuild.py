import logging
import os
import subprocess

from xcrun import XCRun

here = os.path.dirname(__file__)
logging.getLogger(__name__).addHandler(logging.NullHandler())


class XCodeBuild(object):
    binary = './gradlew'
    logger = logging.getLogger()
    xcrun = XCRun()

    def __init__(self, log):
        self.log = log

    def test(self, identifier):
        self.xcrun.launch()
        #os.chdir('/Users/isabelrios/git/fenix/')
        # Change path accordingly to go to root folder to run gradlew
        os.chdir('/Users/isabelrios/git/isabelrios/fenix/')
        args = './gradlew ' + 'app:connectedX86DebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=org.mozilla.fenix.ui.SyncIntegrationTests.SyncIntegrationTest#{}'.format(identifier)
        os.system(args)

        # This part below does not work yet...
        '''
        self.logger.info('Running: {}'.format(' '.join(args)))
        try:
            out = subprocess.check_output(
                args,
                cwd=os.path.join(here, os.pardir),
                stderr=subprocess.STDOUT)
        except subprocess.CalledProcessError as e:
            out = e.output
            raise
        finally:
            with open(self.log, 'w') as f:
                f.writelines(out)
        '''
