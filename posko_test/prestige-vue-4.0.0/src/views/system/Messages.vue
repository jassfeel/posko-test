<script setup>
import { onMounted, ref } from 'vue';

class CustomTextEditor {
    constructor(props) {
        const el = document.createElement('input');
        const { maxLength } = props.columnInfo.editor.options;

        el.type = 'text';
        el.maxLength = maxLength;
        el.value = String(props.value);

        this.el = el;
    }

    getElement() {
        return this.el;
    }

    getValue() {
        return this.el.value;
    }

    mounted() {
        this.el.select();
    }
}

const data = ref([
    {
        id: '10012',
        city: 'Seoul',
        country: 'South Korea',
    },
    {
        id: '10013',
        city: 'Tokyo',
        country: 'Japan',
    },
    {
        id: '10014',
        city: 'London',
        country: 'England',
    },
    {
        id: '10015',
        city: 'Ljubljana',
        country: 'Slovenia',
    },
    {
        id: '10016',
        city: 'Reykjavik',
        country: 'Iceland',
    },
]);

const columns = ref([
    {
        header: 'ID',
        name: 'id',
        editor: {
            type: CustomTextEditor,
            options: {
                maxLength: 10,
            },
        },
    },
    {
        header: 'CITY',
        name: 'city',
        editor: 'text',
    },
    {
        header: 'COUNTRY',
        name: 'country',
        formatter: 'listItemText',
        editor: {
            type: 'select',
            options: {
                listItems: [
                    { text: 'South Korea', value: 'South Korea' },
                    { text: 'England', value: 'England' },
                    { text: 'Japan', value: 'Japan' },
                    { text: 'Slovenia', value: 'Slovenia' },
                ],
            },
        },
    },
]);

const GridTable = ref();

onMounted(() => {
    const grid = GridTable.value;
    const optPreset = {
        selection: {
            background: '#4daaf9',
            border: '#004082',
        },
        scrollbar: {
            background: '#f5f5f5',
            thumb: '#d9d9d9',
            active: '#c1c1c1',
        },
        row: {
            even: {
                background: '#f3ffe3',
            },
            hover: {
                background: '#ccc',
            },
        },
        cell: {
            normal: {
                background: '#fbfbfb',
                border: '#e0e0e0',
                showVerticalBorder: true,
            },
            header: {
                background: '#eee',
                border: '#ccc',
                showVerticalBorder: true,
            },
            rowHeader: {
                border: '#ccc',
                showVerticalBorder: true,
            },
            editable: {
                background: '#fbfbfb',
            },
            selectedHeader: {
                background: '#d8d8d8',
            },
            focused: {
                border: '#418ed4',
            },
            disabled: {
                text: '#b0b0b0',
            },
        },
    };
    grid?.applyTheme('striped', optPreset);
    grid?.setLanguage('ko');
    // const instance = grid?.gridInstance;
    // instance?.setWidth(500);

    //data Load
    // const loginInfo = {};

    // const response = getData();
});
</script>

<template>
    <div class="card">
        <Accordion :activeIndex="0">
            <AccordionTab header="Header I" class="w-full">
                <tui-grid ref="GridTable" :data="data" :columns="columns"> </tui-grid>
            </AccordionTab>
            <AccordionTab header="Header II">
                <p class="m-0">
                    Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim
                    ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Consectetur, adipisci velit, sed quia non numquam eius modi.
                </p>
            </AccordionTab>
            <AccordionTab header="Header III">
                <p class="m-0">
                    At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui
                    officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus.
                </p>
            </AccordionTab>
        </Accordion>
    </div>
</template>
