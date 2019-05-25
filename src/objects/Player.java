package objects;

import enums.AgentType;

public class Player {
    private final boolean began;
    private AgentType agentType;

    public Player(boolean vBegan, AgentType vAgentType){
        this.began = vBegan;
        this.agentType = vAgentType;
    }

    public boolean hasBegun() {
        return this.began;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }
}
