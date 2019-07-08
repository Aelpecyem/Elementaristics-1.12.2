package de.aelpecyem.elementaristics.capability.chunk;

import de.aelpecyem.elementaristics.capability.player.souls.Soul;

public interface IChunkCapabilities {
    int getInfluenceId();

    Soul getInfluence();

    void setInfluenceId(int id);

    void setInfluence(Soul soul); //only non-legendary souls
    //might do that with aspects, too
}
