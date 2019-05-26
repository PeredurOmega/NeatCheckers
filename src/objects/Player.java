package objects;

import enums.AgentType;

public class Player {
    private final boolean began;
    private final AgentType agentType;

    /**
     * Builds a player.
     * @param vBegan Boolean representing the player who began (always the whites).
     * @param vAgentType AgentType representing the type of the player (Human, AI...).
     */
    Player(boolean vBegan, AgentType vAgentType){
        this.began = vBegan;
        this.agentType = vAgentType;
    }

    /**
     * Returns the player's AgentType.
     * @return AgentType representing the type of the player (Human, AI...).
     */
    public AgentType getAgentType() {
        return agentType;
    }
}
