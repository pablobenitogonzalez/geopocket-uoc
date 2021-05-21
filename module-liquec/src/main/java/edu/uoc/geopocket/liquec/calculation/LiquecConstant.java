package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.liquec.common.LiquecCode;

public enum LiquecConstant {
   NO_CHECK_DEPTH("No check depth", 20.0, 0.0),;

   private final String description;
   private final Double eurocode;
   private final Double ncse02;

   LiquecConstant(final String description, final Double eurocode, final Double ncse02) {
        this.description = description;
        this.eurocode = eurocode;
        this.ncse02 = ncse02;
   }

   public String getDescription() {
       return description;
   }

    public Double getValue(final LiquecCode code) {
       if (LiquecCode.EUROCODE == code) {
           return eurocode;
       }
       return ncse02;
   }

}
