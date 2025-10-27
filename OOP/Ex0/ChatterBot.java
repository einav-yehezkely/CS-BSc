import java.util.*;

/**
 * A simple ChatterBot that can reply to a given statement.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix inside a phrase from a few possible phrases
 * as supplied to it via its constructor.
 * Otherwise, it returns one of a few possible replies as supplied to
 * it via its constructor.
 * @author einavye
 */
class ChatterBot {
    /** Prefix indicating a legal request */
	static final String REQUEST_PREFIX = "say ";
    /** Placeholders in the reply where the given phrase should be inserted */
    static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
    static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";

    /** Random generator for choosing a random reply pattern */
	Random rand = new Random();
    /** The name of the bot */
    String name;
    /** Possible replies for legal/illegal requests */
    String[] legalRequestsReplies;
    String[] illegalRequestsReplies;

    /**
     * Constructor
     * @param name the name of the bot
     * @param legalRequestsReplies replies for legal requests
     * @param illegalRequestsReplies replies for illegal requests
     */
	ChatterBot(String name, String[] legalRequestsReplies, String[] illegalRequestsReplies) {
        this.name = name;
        //deep copy of the arrays
		this.illegalRequestsReplies = new String[illegalRequestsReplies.length];
		for(int i = 0 ; i < illegalRequestsReplies.length ; i = i+1) {
			this.illegalRequestsReplies[i] = illegalRequestsReplies[i];
		}
        this.legalRequestsReplies = new String[legalRequestsReplies.length];
        for(int i = 0 ; i < legalRequestsReplies.length ; i++){
            this.legalRequestsReplies[i] = legalRequestsReplies[i];
        }
	}

    /**
     * @return the name of the bot
     */
    String getName(){
        return this.name;
    }

    /**
     * Reply to a given statement.
     * If the statement starts with REQUEST_PREFIX, reply with a legal request reply.
     * Otherwise, reply with an illegal request reply.
     * @param statement the given statement
     * @return the bot's reply
     */
    String replyTo(String statement) {
        //check if the statement is a legal request
        if(statement.startsWith(REQUEST_PREFIX)) {
            return replyToLegalRequest(statement);
        }
        //check if the statement is an echo request. If so, return the echoed phrase
        if(statement.startsWith("echo ")){
            return statement.replaceFirst("echo ", "");
        }
        //otherwise, it's an illegal request
        return replyToIllegalRequest(statement);
    }

    /**
     * Reply to an illegal request
     * @param statement the given statement
     * @return the bot's reply
     */
    String replyToIllegalRequest(String statement) {
        return replacePlaceholderInARandomPattern(
                illegalRequestsReplies, PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement
        );
    }

    /**
     * Reply to a legal request
     * @param statement the given statement
     * @return the bot's reply
     */
    String replyToLegalRequest(String statement){
        String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
        return replacePlaceholderInARandomPattern(
                legalRequestsReplies, PLACEHOLDER_FOR_REQUESTED_PHRASE, phrase
        );
    }

    /**
     * Replace the placeholder in a random pattern from the given patterns with the given replacement
     * @param patterns the possible patterns
     * @param placeholder the placeholder to replace
     * @param replacement the replacement for the placeholder
     * @return the pattern with the placeholder replaced by the replacement
     */
    String replacePlaceholderInARandomPattern(String[] patterns, String placeholder, String replacement){
        int randomIndex = rand.nextInt(patterns.length);
        String reply = patterns[randomIndex];
        return reply.replaceAll(placeholder, replacement);
    }
}
