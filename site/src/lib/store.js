import {writable} from "svelte/store";


export const STATE = writable("MENU")

export const GAME = writable({})

export const USERNAME = writable("")

export const ROLE = writable("UNKNOWN")

export const DECISION = writable(false)

export const QUESTION_STATE = writable(null)
export const QUESTION = writable({})

export const RESULT = writable(null)