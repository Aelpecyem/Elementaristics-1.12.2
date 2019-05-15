package de.aelpecyem.elementaristics.capability.player;

import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.entity.EntityCultist;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;

import java.util.List;

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

    int getCultistCount();

    void setCultistCount(int count);

    int getAscensionRoute();

    void setAscensionRoute(int route);

    Soul getSoul();

    void setSpellSlot(int slot);

    int getSpellSlot();

    void cycleSlot(boolean down);

    SpellBase getCurrentSpell();
}
