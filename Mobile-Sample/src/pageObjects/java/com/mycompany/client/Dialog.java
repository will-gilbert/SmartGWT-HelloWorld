package com.mycompany.client;


interface Dialog {

    String getTitle();

    void selectYes();

    void selectNo();

    void selectOutside();

    boolean isDisplayed();
}