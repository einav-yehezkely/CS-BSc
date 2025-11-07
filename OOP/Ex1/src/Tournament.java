class Tournament{
    int rounds;
    Renderer renderer;
    Player player1;
    Player player2;

    public Tournament(int rounds, Renderer renderer, Player player1, Player player2){
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playTournament(int size, int winStreak, String playerName1, String playerName2){
        int player1_wins = 0;
        int player2_wins = 0;
        int ties_count = 0;
        Player playerX;
        Player playerO;
        for(int i = 0 ; i < rounds ; i++){
            if(i%2 == 0){
                playerX = this.player1;
                playerO = this.player2;
            }
            else{
                playerX = this.player2;
                playerO = this.player1;
            }
            Game game = new Game(playerX, playerO, size, winStreak, this.renderer);
            Mark winner = game.run();
            if (winner == Mark.X) {
                if (playerX == this.player1) {
                    player1_wins++;
                } else {
                    player2_wins++;
                }
            } else if (winner == Mark.O) {
                if (playerO == this.player1) {
                    player1_wins++;
                } else {
                    player2_wins++;
                }
            } else if (winner == Mark.BLANK) {
                ties_count++;
            }
        }
        System.out.println("######### Results #########");
        System.out.println("Player 1, " + playerName1 + " won: " + player1_wins + " rounds");
        System.out.println("Player 2, " + playerName2 + " won: " + player2_wins + " rounds");
        System.out.println("Ties: " + ties_count);

    }

    public static void main(String[] args){
        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        String renderTarget = args[3];
        String playerName1 = args[4];
        String playerName2 = args[5];
        PlayerFactory playerFactory = new PlayerFactory();
        Player player1 = playerFactory.buildPlayer(playerName1);
        Player player2 = playerFactory.buildPlayer(playerName2);
        RendererFactory rendererFactory = new RendererFactory();
        Renderer renderer = rendererFactory.buildRenderer(renderTarget, size);
        Tournament tournament = new Tournament(rounds, renderer, player1, player2);
        tournament.playTournament(size, winStreak, playerName1, playerName2);
    }
}