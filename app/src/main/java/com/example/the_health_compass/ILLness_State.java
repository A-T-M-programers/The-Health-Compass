package com.example.the_health_compass;

public class ILLness_State extends  Type
{
  String IL_Synptoms , IL_Treatment,  IL_Protection;

    public String getIL_Protection() {
        return IL_Protection;
    }

    public String getIL_Synptoms() {
        return IL_Synptoms;
    }

    public String getIL_Treatment() {
        return IL_Treatment;
    }

    public void setIL_Protection(String IL_Protection) {
        this.IL_Protection = IL_Protection;
    }

    public void setIL_Synptoms(String IL_Synptoms) {
        this.IL_Synptoms = IL_Synptoms;
    }

    public void setIL_Treatment(String IL_Treatment) {
        this.IL_Treatment = IL_Treatment;
    }

}
