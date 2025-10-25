import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * @author Dan Nirel
 */
class ChatterBot {
	static final String REQUEST_PREFIX = "say ";
    static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
    static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";

	Random rand = new Random();
	String[] illegalRequestsReplies;
    String name;
    String[] legalRequestsReplies;

	ChatterBot(String name, String[] legalRequestsReplies, String[] illegalRequestsReplies) {
		this.illegalRequestsReplies = new String[illegalRequestsReplies.length];
		for(int i = 0 ; i < illegalRequestsReplies.length ; i = i+1) {
			this.illegalRequestsReplies[i] = illegalRequestsReplies[i];
		}
        this.name = name;
        this.legalRequestsReplies = new String[legalRequestsReplies.length];
        for(int i = 0 ; i < legalRequestsReplies.length ; i++){
            this.legalRequestsReplies[i] = legalRequestsReplies[i];
        }
	}

	String replyTo(String statement) {
		if(statement.startsWith(REQUEST_PREFIX)) {
			//we don’t repeat the request prefix, so delete it from the reply
			return replyToLegalRequest(statement);
		}
        if(statement.startsWith("echo ")){
            return statement.replaceFirst("echo ", "");
        }
		return replyToIllegalRequest(statement);
	}

	String replyToIllegalRequest(String statement) {
        return replacePlaceholderInARandomPattern(illegalRequestsReplies, PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement);
	}

    String getName(){
        return this.name;
    }

    String replyToLegalRequest(String statement){
        String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
        return replacePlaceholderInARandomPattern(legalRequestsReplies, PLACEHOLDER_FOR_REQUESTED_PHRASE, phrase);
    }

    String replacePlaceholderInARandomPattern(String[] patterns, String placeholder, String replacement){
        int randomIndex = rand.nextInt(patterns.length);
        String reply = patterns[randomIndex];
        return reply.replaceAll(placeholder, replacement);
    }
}
