<script>
    import {GAME, STATE, USERNAME} from "$lib/store.js";
    import {createRoom, joinRoom, roomExists, setAdmin} from "$lib/room.js";

    let codeState = true;
    let codeEntered;
    let username;
</script>

<section class="container">
    {#if codeState}
        <h1>Imperfect Baseball</h1>
        <input bind:value={codeEntered} on:keypress={async (e) => {
        if (e.key === "Enter") {
            let exists = await roomExists(codeEntered);
            if (exists) {
                codeState = false;
                return;
            }

            alert("Invalid code")
        }
    }} type="text" placeholder="game code"> <br>

        <a on:click={async () => {
        let game = await createRoom();
        console.log("game " + JSON.stringify(game))
        setAdmin(game.gameCode)
        GAME.set(game);
        STATE.set("LOBBY")
    }}>Create room
        </a>
    {:else}
        <h1>{codeEntered}</h1>
        <input bind:value={username} on:keypress={async (e) => {
        if (e.key === "Enter") {
            USERNAME.set(username);
            GAME.set({
                "gameCode": codeEntered
            })
            joinRoom(codeEntered, username)
            STATE.set("LOBBY")
        }
    }} type="text" placeholder="username">
    {/if}
</section>

<style>
    a {
        font-size: 0.5rem;
        text-align: left;
    }

    .container {
        margin-top: 5rem;
        text-align: center;
        margin-left: auto;
        margin-right: auto;
        max-width: 300px;
        font-family: Arial;
    }
</style>