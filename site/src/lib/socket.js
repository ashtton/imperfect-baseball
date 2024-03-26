import ioClient from 'socket.io-client';
import {DECISION, GAME, QUESTION_STATE, RESULT, ROLE, STATE, USERNAME} from "$lib/store.js";
import {chooseQuestion} from "$lib/room.js";
const ENDPOINT = 'ws://142.4.216.95:3333';

export const socket = ioClient(ENDPOINT);

socket.on('assignRole', (role) => {
    ROLE.set(role)
})

socket.on('endGame', (reason) => {
    STATE.set("MENU")
    GAME.set({})
    USERNAME.set("")
    ROLE.set("")
    QUESTION_STATE.set(false)

    alert(reason)
})

socket.on('updateGame', (data) => {
    GAME.set(JSON.parse(data))
    console.log(data)
})

socket.on('startGame', () => {
    chooseQuestion()
    STATE.set("PLAYING")
})

socket.on('nextPitch', () => {
    chooseQuestion()
    QUESTION_STATE.set(null)
    DECISION.set(false);
})

socket.on('result', (result) => {
    RESULT.set(result)
})