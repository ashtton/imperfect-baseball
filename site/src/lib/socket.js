import ioClient from 'socket.io-client';
import {DECISION, GAME, RESULT, ROLE, STATE, USERNAME} from "$lib/store.js";
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

    alert(reason)
})

socket.on('updateGame', (data) => {
    GAME.set(JSON.parse(data))
    console.log(data)
})

socket.on('startGame', () => {
    STATE.set("PLAYING")
})

socket.on('nextPitch', () => {
    DECISION.set(false);
})

socket.on('result', (result) => {
    RESULT.set(result)
})