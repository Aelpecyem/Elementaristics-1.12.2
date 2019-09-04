package de.aelpecyem.elementaristics.capability.player;

import de.aelpecyem.elementaristics.capability.player.souls.Soul;
import de.aelpecyem.elementaristics.init.SoulInit;
import de.aelpecyem.elementaristics.misc.spell.SpellBase;

public class PlayerCapabilities implements IPlayerCapabilities {

    private int soulId = -1;
    private int ticksLeftStunted = 0;
    private float maxMagan = 100;
    private float currentMagan = maxMagan;
    private float maganRegenPerTick;

    private int cultistCount;

    private int ascensionStage;
    private int ascensionRoute;
    private boolean knowsSoul = false;

    private int spellSlot;

    private float visionProgression;
    private String visionActive;

    public PlayerCapabilities() {
        soulId = SoulInit.generateSoulType().getId();
        visionActive = "";
    }

    @Override
    public int getSoulId() {
        return soulId;
    }

    @Override
    public void setSoulId(int id) {
        this.soulId = id;
    }

    @Override
    public void setKnowsSoul(boolean b) {
        this.knowsSoul = b;
    }

    @Override
    public boolean knowsSoul() {
        return knowsSoul;
    }


    @Override
    public float getMaxMagan() {
        return maxMagan;
    }


    @Override
    public void setMaxMagan(float newMax) {
        maxMagan = newMax;
    }

    @Override
    public boolean drainMagan(float amount) {
        if (currentMagan >= amount) {
            currentMagan -= amount;
            return true;
        } else {
            currentMagan = 0;
            return false;
        }
    }

    @Override
    public void fillMagan(float amount) {
        if ((currentMagan + amount) <= getMaxMagan()) {
            currentMagan += amount;
        } else {
            currentMagan = getMaxMagan();
        }
    }

    @Override
    public void stuntMagan(int ticksStunted) {
        this.ticksLeftStunted = ticksStunted;
    }

    @Override
    public int getTimeStunted() {
        return ticksLeftStunted;
    }

    @Override
    public float getMagan() {
        return currentMagan;
    }

    @Override
    public void setMagan(float magan) {
        currentMagan = magan;
    }

    @Override
    public float getMaganRegenPerTick() {
        return maganRegenPerTick;
    }

    @Override
    public void setMaganRegenPerTick(float amount) {
        maganRegenPerTick = amount;
    }

    @Override
    public void setPlayerAscensionStage(int stage) {
        this.ascensionStage = stage;
    }

    @Override
    public int getPlayerAscensionStage() {
        return ascensionStage;
    }

    @Override
    public int getCultistCount() {
        return cultistCount;
    }

    @Override
    public void setCultistCount(int count) {
        cultistCount = count;
    }

    @Override
    public int getAscensionRoute() {
        return ascensionRoute;
    }

    @Override
    public void setAscensionRoute(int spellSlot) {
        this.ascensionRoute = spellSlot;
    }


    @Override
    public Soul getSoul() {
        return SoulInit.getSoulFromId(soulId);
    }

    @Override
    public void setSpellSlot(int slot) {
        this.spellSlot = slot;
    }

    @Override
    public int getSpellSlot() {
        return spellSlot;
    }

    @Override
    public void cycleSlot(boolean down) {
        if (down) {
            if (spellSlot > 0) {
                spellSlot--;
            } else {
                spellSlot = getSoul().getSpellList().size() - 1;
            }
        } else {
            if (spellSlot < getSoul().getSpellList().size() - 1) {
                spellSlot++;
            } else {
                spellSlot = 0;
            }
        }
    }

    @Override
    public SpellBase getCurrentSpell() {
        try {
            return getSoul().isSpellUsable(getSoul().getSpellList().get(spellSlot), this) ? getSoul().getSpellList().get(spellSlot) : null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public float getVisionProgression() {
        return visionProgression;
    }

    @Override
    public boolean isVisionActive() {
        return visionActive != null && !visionActive.isEmpty();
    }

    @Override
    public void setVisionProgression(float progression) {
        this.visionProgression = progression;
    }

    @Override
    public void setVision(String vision) {
        if (vision != visionActive) {
            setVisionProgression(0);
        }
        this.visionActive = vision;
    }

    @Override
    public String getVision() {
        return visionActive != null ? visionActive : "";
    }

    @Override
    public void updateVision() { //might not need to be synced, as it's ONLY used Client-Side
        if (isVisionActive() && getVisionProgression() < 1) {
            setVisionProgression(getVisionProgression() + 0.003F);
        } else {
            setVisionProgression(0);
            setVision("");
        }
    }
}
