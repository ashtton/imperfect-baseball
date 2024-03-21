<script>
    import Menu from '$lib/pages/menu/menu.svelte'

    import {createRoom} from "$lib/room.js";
    import {ROOM_CODE, STATE} from "$lib/store.js";
</script>

<main>
    {#if $STATE === "MENU"}
        <Menu />
    {/if}
</main>