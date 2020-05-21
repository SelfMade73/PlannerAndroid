package com.example.myapplication;

class InstructionItem {
    private String instructionTitle,instructionDescription;
    private int  introImg;

    InstructionItem(String instructionTitle, String instructionDescription, int introAnim) {
        this.instructionTitle = instructionTitle;
        this.instructionDescription = instructionDescription;
        this.introImg = introAnim;
    }

    String getInstructionTitle() {
        return instructionTitle;
    }

    String getInstructionDescription() {
        return instructionDescription;
    }

    int getIntroImg() {
        return introImg;
    }
}
