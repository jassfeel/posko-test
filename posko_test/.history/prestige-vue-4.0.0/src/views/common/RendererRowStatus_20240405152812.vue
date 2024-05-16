<template>
    <span class="material-icons-outlined">{{ imgForRowStatus }}</span>
</template>

<script>
    export default {
        data() {
            return {
                rowStatus: '',
                imgForRowStatus: null
            };
        },
        methods: {
            refresh(params) {
                this.params = params;
                this.setRowStatus(params);
            },

            setRowStatus(params) {
                this.mood = params.value;
                this.imgForRowStatus = this.rowStatus === 'N' ? 'add' 
                                            : this.rowStatus === 'D' ? 'delete_forever' 
                                                : this.rowStatus === 'U' ? 'update' 
                                                    : this.rowStatus;
            }
        },
        created() {
            this.setRowStatus(this.params);
        }
    };
</script>

<style scoped></style>
