package simpleTune.tts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.sun.speech.freetts.Voice;
//import com.sun.speech.freetts.VoiceManager;

public class Speaker {
	private Log logger = LogFactory.getLog(getClass());
    private static final String VOICE_NAME = "kevin16";
    //private static final VoiceManager VOICE_MANAGER = VoiceManager.getInstance();
    //private static final Voice VOICE = VOICE_MANAGER.getVoice(VOICE_NAME);

    static {
        //VOICE.allocate();
    }

    private Speaker() {
        throw new UnsupportedOperationException();
    }

    public static void say(final String message) {
    	/*
        runAsDaemon(new Runnable() {
            public void run() {
                try {
                    VOICE.speak(message);
                } catch (Exception e) {
                    // ignore
                }
            }
        });
        */
    }

    public static void end() {
        //VOICE.deallocate();
    }
}
