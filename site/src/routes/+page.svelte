<script>
    import Menu from '$lib/pages/menu/menu.svelte'
    import Lobby from '$lib/pages/lobby/lobby.svelte'
    import Playing from '$lib/pages/playing/playing.svelte'

    import {createRoom} from "$lib/room.js";
    import {STATE} from "$lib/store.js";

    import {onMount} from "svelte";
    import {socket} from "$lib/socket.js";
</script>

<main>
    <h1>Imperfect Baseball</h1>
    <hr>

    {#if $STATE === "MENU"}
        <Menu />
    {/if}

    {#if $STATE === "LOBBY"}
        <Lobby />
    {/if}

    {#if $STATE === "PLAYING"}
        <Playing />
    {/if}
</main>

<style>
    :global(body) {
        font-family: Arial;
    }

    main {
        max-width: 500px;
        margin: auto;
    }

    h1 {
        text-align: center;
    }
</style>