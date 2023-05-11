/*
 * Created on May 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package logger.utec.tts;

import logger.utec.properties.UtecProperties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.tts.Speaker;

public final class UtecSpeaker {
	private Log logger = LogFactory.getLog(getClass());
    private UtecSpeaker() {
        throw new UnsupportedOperationException();
    }

    public static void say(String message) {
        String[] toSpeak = UtecProperties.getProperties("utec.sound");
        boolean parsedBoolean = Boolean.parseBoolean(toSpeak[0]);
        if (parsedBoolean) {
            Speaker.say(message);
        }
    }
}
