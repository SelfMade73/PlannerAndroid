package com.example.myapplication.models;

public class InstructionItem {
    private String instructionTitle,instructionDescription;
    private int  introImg;

    public InstructionItem(String instructionTitle, String instructionDescription, int introAnim) {
        this.instructionTitle = instructionTitle;
        this.instructionDescription = instructionDescription;
        this.introImg = introAnim;
    }

    public String getInstructionTitle() {
        return instructionTitle;
    }

    public String getInstructionDescription() {
        return instructionDescription;
    }

    public int getIntroImg() {
        return introImg;
    }
}
