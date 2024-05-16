const agGridService = {
    /**
     * 그리드의 데이타를 전체/변경된 것을 가져온다
     * @param {*} pGridApi 
     * @param {*} pOnlyUpdated 
     */
    agGridGetData: (pGridApi, pOnlyUpdated) => {
        console.log("agGridGetData");
        let updatedData = [];
        let sRowStatus = "N,U,D";
        pGridApi.value.forEachNode(rec => {
            if (pOnlyUpdated) {
                if (sRowStatus.indexOf(rec.data.rowStatus) != -1) {
                    updatedData.push(rec.data);
                }
            } else {
                updatedData.push(rec.data);
            } 
        }); 
        return updatedData;
    },

    /**
     * 새로운행울 추가하고 해당 결과에 대한 정보를 배열로 돌려준다
     * @param {*} pGridApi 
     * @returns 
     */
    agGridAddRow: (pGridApi, pRowCnt, pIdx) => {
        console.log("agGridAddRow");
        let newRows = [];
        for (let i=0; i<pRowCnt; i++) {
            let newData = {
                rowStatus: "N"
            };
            newRows.push(newData);
        }
        const res = pGridApi.value.applyTransaction({
            addIndex: pIdx,
            add: newRows
        });
        return res;
    },

    /**
     * 레코드를 삭제처리하고 결과를 돌려준다
     * @param {*} pGridApi 
     * @returns 
     */
    agGridDelRow: (pGridApi, pSelectedNodes) => {
        console.log("agGridDelRow");

        let removeNodes = [];
        let updateNodes = [];
        pSelectedNodes.map(
            curNode => {
                if (curNode.data.rowStatus === "N") {
                    removeNodes.push(curNode.data);
                } else {
                    curNode.data.rowStatus = "D";
                    updateNodes.push(curNode.data);
                }
            }
        )

        const res = pGridApi.value.applyTransaction({
            remove: removeNodes,
            update: updateNodes
        });    
        return res;
    },

    /**
     * 레코드의 변경상태를 초기화 한다(신규레코드는 제외)
     * @param {*} pGridApi 
     * @returns 
     */
    agGridEraseStatusRow: (pGridApi) => {
        console.log("agGridEraseStatusRow");
        const selectedRows = pGridApi.value.getSelectedRows();

        if ("U,D".indexOf(selectedRows[0].rowStatus) > -1) {
            selectedRows.map(curRec => curRec.rowStatus = "");
            const res = gridApi.value.applyTransaction({
                update: selectedRows
            });    
            return res;
        }

        return res;
    },

    /**
     * 셀변경시 상태 처리
     * @param {*} pGridApi 
     * @returns 
     */
    agGridChangeStatusRow: (pGridApi, pEvent) => {
        console.log("onCellValueChanged");
        if (pEvent.data.rowStatus == "") pEvent.data.rowStatus = "U";    
        const res = pGridApi.value.applyTransaction({
            update: [pEvent.data]
        });    

        return res;
    },

    /**
     * 디폴트 셀 스타일 적용
     * @param {*} pGridApi 
     * @returns 
     */
    agGridDefaultCellStyle: (params) => {
        console.log("agGridDefaultCellStyle");
        // 셀이 읽기 전용인 경우 배경색을 회색으로 변경
        if (params.node && params.node.rowPinned) {
            return { background: "lightgray" };
        } else {
            return null;
        }
    }

}
export default agGridService;