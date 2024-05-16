<template>
    <i :class="imgForRowStatus" aria-hidden="true"></i>
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
                this.imgForRowStatus = this.rowStatus === 'N' ? 'fa fa-plus' 
                                            : this.rowStatus === 'D' ? 'fa fa-minus' 
                                                : this.rowStatus === 'U' ? 'fa fa-refresh' 
                                                    : this.rowStatus;
            }
        },
        created() {
            this.setRowStatus(this.params);
        }
    };
</script>

<style scoped></style>
