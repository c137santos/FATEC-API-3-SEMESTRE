<template>
    <button
        class="cerb-btn ptb-small plr-small"
        :class="!fillType || fillType === 'full' ? 'full' : fillType === 'revert' ? 'revert' : 'mute'"
        :disabled="disabled"
    >
        <slot></slot>
    </button>
</template>

<script setup lang="ts">
type Props = {
    fillType?: 'full' | 'revert' | 'mute'
    disabled?: boolean
}

const { fillType, disabled } = defineProps<Props>()

</script>

<style scoped>
.cerb-btn {
    border: 0;
    border-radius: 8px;

    cursor: pointer;
    transition: all 250ms ease-in-out;
}

.cerb-btn:disabled {
    background-color: #c0c0c0;
    cursor:auto;
}

.full {
    background-color: #65558F;
    color: white;
}

.full:hover:not(:disabled) {
    background-color: #9783ca;
}

.revert {
    background-color: transparent;
    color: #65558F;
}

.revert:disabled {
    background-color: transparent;
    color: #c0c0c0;
}

.revert:hover:not(:disabled) {
    color: #9783ca;
}

.mute {
    background-color: rgb(37, 37, 37);
    color: white;
}
.mute:hover:not(:disabled) {
    background-color: rgb(82, 82, 82);
}
</style>