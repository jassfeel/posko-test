<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <h5>Calendar</h5>
                <FullCalendar :events="events" :options="options" />

                <Dialog v-model:visible="eventDialog" :style="{ width: '450px' }" header="Event Details" :modal="true" :closable="true">
                    <div class="p-fluid">
                        <div class="field">
                            <label for="title">Title</label>
                            <InputText id="title" v-if="clickedEvent" v-model="changedEvent.title" required="true" autofocus />
                        </div>
                        <div class="field">
                            <label for="start">From</label>
                            <Calendar id="start" v-if="clickedEvent" v-model="changedEvent.start" :showTime="true" appendTo="body" />
                        </div>
                        <div class="field">
                            <label for="end">To</label>
                            <Calendar id="end" v-if="clickedEvent" v-model="changedEvent.end" :showTime="true" appendTo="body" />
                        </div>
                        <div class="field-checkbox">
                            <Checkbox id="allday" name="allday" value="All Day" v-model="changedEvent.allDay" />
                            <label for="allday">All Day</label>
                        </div>
                    </div>
                    <template #footer>
                        <Button label="Save" icon="pi pi-check" class="p-button-text" @click="save" />
                        <Button label="Reset" icon="pi pi-refresh" class="p-button-text" @click="reset" />
                    </template>
                </Dialog>
            </div>
        </div>
    </div>
</template>

<script>
import '@fullcalendar/core/vdom';
import EventService from '@/service/EventService';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import FullCalendar from 'primevue/fullcalendar';

export default {
    components: {
        FullCalendar,
    },
    data() {
        return {
            eventDialog: false,
            clickedEvent: null,
            changedEvent: { title: '', start: null, end: '', allDay: null },
            options: {
                plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
                initialDate: '2023-01-01',
                headerToolbar: {
                    left: 'prev,next',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay',
                },
                editable: true,
                selectable: true,
                selectMirror: true,
                dayMaxEvents: true,
                eventClick: (e) => {
                    this.eventDialog = true;
                    this.clickedEvent = e.event;
                    this.changedEvent.title = this.clickedEvent.title;
                    this.changedEvent.start = this.clickedEvent.start;
                    this.changedEvent.end = this.clickedEvent.end;
                },
            },
            events: null,
        };
    },
    eventService: null,
    created() {
        this.eventService = new EventService();
    },
    mounted() {
        this.eventService.getEvents().then((data) => (this.events = data));
    },
    methods: {
        findIndexById(id) {
            let index = -1;
            for (let i = 0; i < this.events.length; i++) {
                if (this.events[i].id === id) {
                    index = i;
                    break;
                }
            }
            return index;
        },
        save() {
            this.eventDialog = false;
            this.clickedEvent.setProp('title', this.changedEvent.title);
            this.clickedEvent.setStart(this.changedEvent.start);
            this.clickedEvent.setEnd(this.changedEvent.end);
            this.clickedEvent.setAllDay(this.changedEvent.allDay);
            this.changedEvent = { title: '', start: null, end: '', allDay: null };
        },
        reset() {
            this.changedEvent.title = this.clickedEvent.title;
            this.changedEvent.start = this.clickedEvent.start;
            this.changedEvent.end = this.clickedEvent.end;
        },
    },
};
</script>

<style scoped>
::v-deep(.fc .fc-col-header-cell-cushion),
::v-deep(.fc-daygrid-dot-event .fc-event-time),
::v-deep(.fc-daygrid-dot-event .fc-event-title),
::v-deep(.fc .fc-daygrid-day-number),
::v-deep(.fc .fc-daygrid-more-link) {
    color: var(--text-color);
}

@media screen and (max-width: 960px) {
    ::v-deep(.fc-header-toolbar) {
        display: flex;
        flex-wrap: wrap;
    }
}
</style>
