<script>

    import {ROOM_CODE, STATE} from "$lib/store.js";
    import {createRoom} from "$lib/room.js";
</script>

<section class="container">
    <h1>Imperfect Baseball</h1>
    <input type="text" placeholder="game code"> <br>

    <a href="#" on:click={async () => {
        let roomCode = await createRoom();
        ROOM_CODE.set(roomCode);
        STATE.set("LOBBY")
    }}>Create room
    </a>
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
        max-width: 600px;
        font-family: Arial;
    }
</style>