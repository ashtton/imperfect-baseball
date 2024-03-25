<script>
    import Menu from '$lib/pages/menu/menu.svelte'
    import Lobby from '$lib/pages/lobby/lobby.svelte'

    import {createRoom} from "$lib/room.js";
    import {STATE} from "$lib/store.js";

    import {onMount} from "svelte";
    import {socket} from "$lib/socket.js";
</script>

<main>
    {#if $STATE === "MENU"}
        <Menu />
    {/if}

    {#if $STATE === "LOBBY"}
        <Lobby />
    {/if}
</main>