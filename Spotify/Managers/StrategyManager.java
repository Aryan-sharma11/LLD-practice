package Spotify.Managers;
import Spotify.Strategy.PlayStrategy;
import Spotify.Strategy.Sequential;
import Spotify.Strategy.RandomStrategy;
import Spotify.Strategy.Custom;
import Spotify.Enums.StrategyType;
public class StrategyManager {
    private static StrategyManager instance = null;
    private Sequential sequentialStrategy;
    private RandomStrategy randomStrategy;
    private Custom customQueueStrategy;
    private StrategyManager() {
        sequentialStrategy = new Sequential();
        randomStrategy = new RandomStrategy();
        customQueueStrategy = new Custom();
    }

    public static synchronized StrategyManager getInstance() {
        if (instance == null) {
            instance = new StrategyManager();
        }
        return instance;
    }

    public PlayStrategy getStrategy(StrategyType type) {
        if (type == StrategyType.Sequential) {
            System.out.println("Sequential Strategy selected");
            return sequentialStrategy;
        } else if (type == StrategyType.Random) {
            System.out.println("Random Strategy selected"); 
            return randomStrategy;
        } else {
            System.out.println("Custom Queue Strategy selected");
            return customQueueStrategy;
        }
    }
}
