import {socket} from "$lib/socket.js";
import {GAME, USERNAME} from "$lib/store.js";

export const createRoom = async() => {
    const res = await fetch("http://localhost:8080/api/room/create", {
        method: "POST"
    })

    return await res.json()
}

export const roomExists = async(code) => {
    const res = await fetch("http://localhost:8080/api/room/" + code + "/exists", {
        method: "GET"
    })

    const boo = await res.text();

    return boo === "true";
}

export const joinRoom = (code, username) => {
    socket.emit("joinRoom", {
        "code": code,
        "username": username
    });
    subscribeToGame(code)
}

export const setAdmin = (code) => {
    socket.emit("setAdmin", {
        "code": code
    })
    subscribeToGame(code)
}

const subscribeToGame = (game) => {
    socket.on('updateGame', (data) => {
        GAME.set(JSON.parse(data))
        console.log(data)
    })
}