package de.aelpecyem.elementaristics.capability.chunk;

import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.init.SoulInit;

public class ChunkCapabilities implements IChunkCapabilities {
    private int influenceId = -1;

    public ChunkCapabilities() {
        Soul influence = SoulInit.generateSoulTypeForChunk();
        influenceId = influence != null ? influence.getId() : -1;
    }

    @Override
    public int getInfluenceId() {
        return influenceId;
    }

    @Override
    public Soul getInfluence() {
        return SoulInit.getSoulFromIdWithNull(influenceId);
    }

    @Override
    public void setInfluenceId(int id) {
        this.influenceId = id;
    }

    @Override
    public void setInfluence(Soul soul) {
        this.influenceId = soul.getId();
    }
}
