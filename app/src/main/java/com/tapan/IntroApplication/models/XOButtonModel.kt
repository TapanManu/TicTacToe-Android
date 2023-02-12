package com.tapan.IntroApplication.models

class XOButtonModel {

    var id = 0
    get() = field
    set(value) {
        field = value
    }
    var playerName : Char = 'D'
    get() = field
    set(value) {
        field = value
    }

    constructor(id: Int, playerName: Char){
        this.id = id;
        this.playerName = playerName;
    }
}