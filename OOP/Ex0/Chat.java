import java.util.Scanner;

class Chat{
    public static void main(String[] args){
        String placeholder = ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE;
        String placeholder_illegal = ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST;
        ChatterBot[] bots = new ChatterBot[2];

        String[][] repliesToIllegalRequests = {
                { "what " + placeholder_illegal + "?", "say I should say " + placeholder_illegal },
                { "say what? " + placeholder_illegal + "? what's " + placeholder_illegal + "?", "say say " + placeholder_illegal }
        };

        String[][] repliesToLegalRequests = {
                { "say " + placeholder + "? okay: " + placeholder },
                { "you want me to say " + placeholder +", do you? alright: " + placeholder }
        };

        bots[0] = new ChatterBot("Garfield", repliesToLegalRequests[0], repliesToIllegalRequests[0]);
        bots[1] = new ChatterBot("Garry", repliesToLegalRequests[1], repliesToIllegalRequests[1]);

        String statement = "echo Hello";

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