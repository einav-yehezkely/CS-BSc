import java.util.Scanner;

/**
 * A simple chat application where two ChatterBots converse with each other.
 * Each bot replies to the other's statements based on predefined patterns.
 * The conversation continues indefinitely, waiting for user input (Enter key) to proceed after each reply.
 * @author einavye
 */
class Chat{
    /** Names of the two bots */
    static final String NAME0 = "Garfield";
    static final String NAME1 = "Garry";
    /**
     * Main method to run the chat application.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args){
        // Placeholders that will be replaced in the bot replies
        String placeholder = ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE;
        String placeholderIllegal = ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST;

        // Create two ChatterBots with their respective reply patterns
        ChatterBot[] bots = new ChatterBot[2];

        String[][] repliesToIllegalRequests = {
                { "what " + placeholderIllegal + "?",
                        "huh? " + placeholderIllegal + "?",
                        "say something",
                        "say say " + placeholderIllegal + ". ok?",


                },
                { "what? " + placeholderIllegal + "?",
                        "what's " + placeholderIllegal + "?",
                        "say say " + placeholderIllegal,
                        "say whaaat"
                }
        };

        String[][] repliesToLegalRequests = {
                { "say " + placeholder + "? ok: " + placeholder,
                        "here you go: " + placeholder,
                        "as you wish: " + placeholder,
                        placeholder
                },
                { "you want me to say " + placeholder +"? alright: " + placeholder,
                        placeholder + ", see? easy!",
                        "you got it: " + placeholder,
                        placeholder + ", ok?"
                }
        };

        bots[0] = new ChatterBot(NAME0, repliesToLegalRequests[0], repliesToIllegalRequests[0]);
        bots[1] = new ChatterBot(NAME1, repliesToLegalRequests[1], repliesToIllegalRequests[1]);

        // Initial statement to start the conversation
        String statement = "echo Hello";

        // Start the conversation loop
        Scanner scanner = new Scanner(System.in);
        while(true){
            for(ChatterBot bot : bots){
                statement = bot.replyTo(statement);
                System.out.print(bot.getName() + ": " + statement);
                scanner.nextLine(); // wait for "enter"
            }
        }

    }
}