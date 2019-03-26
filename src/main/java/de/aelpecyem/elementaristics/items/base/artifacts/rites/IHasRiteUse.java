package de.aelpecyem.elementaristics.items.base.artifacts.rites;

import de.aelpecyem.elementaristics.misc.elements.Aspect;

import java.util.List;

public interface IHasRiteUse {
   public List<Aspect> getAspects();

   public int getPower();

   public boolean isConsumed();
}
