package de.aelpecyem.elementaristics.capability;

public interface IPlayerCapabilities {

    int getSoulId();

    void setSoulId(int soulId);

    void setKnowsSoul(boolean b);

    boolean knowsSoul();


    float getMaxMagan();

    void setMaxMagan(float newMax);

    boolean drainMagan(float amount);

    void fillMagan(float amount);

    void stuntMagan(int ticksStunted);

    int getTimeStunted();

    float getMagan();

    void setMagan(float magan);

    float getMaganRegenPerTick();

    void setMaganRegenPerTick(float amount);

    void setPlayerAscensionStage(int stage);

    int getPlayerAscensionStage();

}
