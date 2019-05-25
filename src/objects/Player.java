package objects;

import enums.AgentType;

import java.util.ArrayList;

public class Player {
    private boolean began;
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
