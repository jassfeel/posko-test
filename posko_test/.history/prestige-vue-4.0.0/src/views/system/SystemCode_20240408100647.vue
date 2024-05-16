<template>
    <div class="card">
        <Toast />
        <div class="card formgrid grid">
            <div class="field col grid">
                <div class="field col-12 md:col-6">
                    <label for="codeType">코드유형</label>
                    <AutoComplete v-model="codeType" dropdown :suggestions="filteredItems" @complete="searchItems" class="w-full" />
                </div>
                <div class="field  col-12 md:col-6">
                    <label for="useAt">사용여부</label>
                    <SelectButton v-model="useAt" :options="options" aria-labelledby="basic" class="w-full" />
                </div>
            </div>
            <SplitButton label="Search" icon="pi pi-plus" @click="search" :model="buttonItems" class="bg-primary" />
        </div>  

        <div class="grid">
            <div class="col-12 md:col-4" style="padding: 0; padding-right: 10px;">
                <ag-grid-vue
                    style="width: 100%; height: 350px;"
                    class="ag-theme-quartz"
                    :columnDefs="columnsType"                        
                    :rowData="codeTypeItems"
                    @grid-ready="onGridReadyType"
                    :rowSelection="rowSelection"
                    @selection-changed="onSelectionChanged"
                >
                </ag-grid-vue>
            </div>

            <div class="col-12 md:col-8" style="padding: 0">
                <ag-grid-vue
                    style="width: 100%; height: 350px;"
                    class="ag-theme-quartz"
                    :columnDefs="columns"                        
                    :rowData="codeItems"
                    :rowSelection="rowSelection"
                    @cell-value-changed ="onCellValueChanged"
                    :defaultColDef="defaultColDef"
                >
                </ag-grid-vue>
            </div>

        </div>
    </div>
</template>

<script setup>

import { useToast } from "primevue/usetoast";
import axios from '../../service/axios';
import { onBeforeMount, ref } from 'vue';
import { useI18n } from 'vue-i18n'
import RendererRowStatus from "../common/RendererRowStatus.vue";

const toast = useToast();

const codeTypeItems = ref("");
const codeItems = ref("");
const codeType = ref("");
const filteredItems = ref();
const useAt = ref("전체");
const options = ref(['전체', '사용', '미사용']);

let data = [];

const i18n = useI18n()
const columnsType = [
    {
        headerName: i18n.t('System.SystemCode.grid.CodeType'),
        field: "cmmnCode",
    },
    {
        headerName: i18n.t('System.SystemCode.grid.CodeName'),
        field: "cmmnCodeNm",
    },
];

const defaultColDef = {
    flex: 1,
    editable: true,
    filter: true,
    enableCellChangeFlash: true,
};

const columns = [
    {
        headerName: "",
        field: "RowStatus",
        editable: false,
        pinned: 'left',
        maxWidth: 30,
        cellRenderer:  RendererRowStatus
        // cellRenderer: params => params.value === "U" ? "U" : " "
    },
    {
        headerName: i18n.t('System.SystemCode.grid.Code'),
        field: "cmmnCode",
        editable: true,
        pinned: 'left',
    },
    {
        headerName: i18n.t('System.SystemCode.grid.CodeName'),
        field: "cmmnCodeNm",
        editable: true,
        pinned: 'left',
    },
    {
        headerName: i18n.t('System.SystemCode.grid.Comment') + "1",
        field: "mngIem1",
        editable: true,
    },
    {
        headerName: i18n.t('System.SystemCode.grid.Comment') + "2",
        field: "mngIem2",
        editable: true,
    },
    {
        headerName: i18n.t('System.SystemCode.grid.Comment') + "3",
        field: "mngIem3",
        editable: true,
    },
    {
        headerName: i18n.t('System.SystemCode.grid.Comment') + "4",
        field: "mngIem4",
        editable: true,
    },
    {
        headerName: i18n.t('System.SystemCode.grid.Order'),
        field: "ordr",
        editable: true,
        cellEditor: 'agNumberCellEditor',
        cellEditorParams: {
            step: 1,
            showStepperButtons: true
        }
    },
    {
        headerName: i18n.t('System.SystemCode.grid.Use'),
        field: "useAt",
        editable: true,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: {
            values: ['Y', 'N'],
        }
    },
    {
        headerName: i18n.t('System.SystemCode.grid.Register'),
        field: "register"
    },
    {
        headerName: i18n.t('System.SystemCode.grid.RegDate'),
        field: "inputDe"
    },
    {
        headerName: i18n.t('System.SystemCode.grid.Updater'),
        field: "updusr"
    },
    {
        headerName: i18n.t('System.SystemCode.grid.UpdDate'),
        field: "updtDe"
    }

];

const getData = async () => {   

    try{    
        const response = await axios.post('/api/code/cmmncode', {lcal: 'KR'});
        console.log('response => ');
        console.log(response);

        console.log('response.status = ' + response.status);
        if (response.status == 200) {
            console.log('Search Datas: ' + JSON.stringify(response.data));

            data = response.data; //조회데이타
            //코드타입 데이타만 업데이트
            codeTypeItems.value = data.filter(rec => rec.codeTy === '000').sort((a, b) => (String(a.ordr).padStart(10, "0") + a.cmmnCode).localeCompare(String(b.ordr).padStart(10, "0") + b.cmmnCode)) ;
            gridApiType.value.sizeColumnsToFit({
                defaultMinWidth: 50
            });
            codeItems.value = [];

            toast.add({ severity: 'success', summary: 'Success', detail: 'Search successfully', life: 3000 });
        }
        return response.status;
    }catch(err){        
        toast.add({ severity: 'error', summary: 'Error', detail: err.message, life: 3000 });
        return err.message;
    }

};

const buttonItems = [
    {
        label: 'Save',
        icon: 'pi pi-save',
        command: () => {
            toast.add({ severity: 'success', summary: 'Saved', detail: 'Data Saved', life: 3000 });
        }
    },
    {
        label: 'Download',
        icon: 'pi pi-file-excel',
        command: () => {
            window.location.href = 'https://vuejs.org/';
        }
    }
];

const search = () => {
    getData();
};

const rowSelection = ref(null);
const gridApiType = ref();
const gridApi = ref();

onBeforeMount(() => {
    rowSelection.value = 'single';
});

const onSelectionChanged = () => {
    const selectedRows = gridApiType.value.getSelectedRows();
    if (selectedRows.length > 0) {
        codeItems.value = data.filter(rec => rec.codeTy === selectedRows[0].cmmnCode).sort(
            (a, b) => (String(a.ordr).padStart(10, "0") + a.cmmnCode).localeCompare(String(b.ordr).padStart(10, "0") + b.cmmnCode)
        ).map(
            rec => {
                rec.RowStatus = "";
                return rec;
            }
        );
    }
};

const onCellValueChanged = (event) => {
    console.log("onCellValueChanged");
    event.data.RowStatus = "U";    
    const res = gridApi.applyTransaction({
        update: event.data,
    });    
    console.log(res);
};
const onGridReadyType = (params) => {
    gridApiType.value = params.api;

    getData();
};
const onGridReady = (params) => {
    gridApi.value = params.api;
};

const searchItems = (event) => {
    let query = event.query;
    let _filteredItems = [];

    for (let i = 0; i < codeTypeItems.value.length; i++) {
        let item = codeTypeItems.value[i];

        if (item.cmmnCodeNm.toLowerCase().indexOf(query.toLowerCase()) >= 0 || item.cmmnCode.toLowerCase().indexOf(query.toLowerCase()) >= 0) {
            _filteredItems.push(item.cmmnCode + " : " + item.cmmnCodeNm);
        }
    }

    filteredItems.value = _filteredItems;
};
</script>
