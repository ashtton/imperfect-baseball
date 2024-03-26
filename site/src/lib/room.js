import {socket} from "$lib/socket.js";
import {DECISION, RESULT} from "$lib/store.js";

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
}

export const setAdmin = (code) => {
    socket.emit("setAdmin", {
        "code": code
    })
}

export const startGame = (code) => {
    socket.emit("startGame", {
        "code": code
    })
}

export const nextPitch = (code) => {
    RESULT.set(null)
    socket.emit("nextPitch", {
        "code": code
    })
}

export const submitDefense = (code, question, strike, pitchType) => {
    socket.emit("setDefense", {
        "code": code,
        "answeredCorrectly": question,
        "strike": strike,
        "pitchType": pitchType
    })

    console.log({
        "code": code,
        "answeredCorrectly": question,
        "strike": strike,
        "pitchType": pitchType
    })
    DECISION.set(true)
}

export const submitOffense = (code, question, swingTiming) => {
    socket.emit("setOffense", {
        "code": code,
        "answeredCorrectly": question,
        "swingTiming": swingTiming
    })
    DECISION.set(true)
}