// convert xlsx file to json for vue-i18n
import Excel from 'exceljs'
import fs from 'fs'

try {
  const workbook = new Excel.Workbook()
  await workbook.xlsx.readFile('src/i18n/i18n.xlsx')

  console.log('Starting loading i18n.xlsx');

  let output = {}

  let langList = []
  // 첫번째 시트 worksheets[0]
  workbook.worksheets[0].eachRow((row, rowNumber) => {
    if (rowNumber == 1) {
      // output 언어 종류 설정
      langList = row.values.slice(5) // excel 파일내 대분류,페이지명,컴포넌트명,용어 떼고 다음 컬럼부터 언어
      langList.forEach((lang) => {
        output[lang] = {}
      })
      // console.log('lang 종류', output)
    }
    if (rowNumber > 1) {
      const rows = row.values
      // console.log('rows', rows)
      // [empty, 'app', 'home', 'pageNames', 'selectLang', '언어선택', 'select languages']
      const category = rows[1]
      const page = rows[2]
      const component = rows[3]
      const target = rows[4]

      langList.forEach((lang, idx) => {
        if (!output[lang][category]) {
          output[lang][category] = {}
        }
        if (!output[lang][category][page]) {
          output[lang][category][page] = {}
        }
        if (!output[lang][category][page][component]) {
          output[lang][category][page][component] = {}
        }
        if (!output[lang][category][page][component][target]) {
          output[lang][category][page][component][target] = {}
        }
        output[lang][category][page][component][target] = rows[idx + 5]
      })
    }
  })

  // 언어별 파일로 저장
  langList.forEach((lang) => {
    fs.writeFile(`src/locales/${lang}.json`, JSON.stringify(output[lang]), (err) => {
      if (err) throw err
    })
  })

  console.log('End loading i18n.xlsx');

} catch (err) {
  console.log('fail to convert xlsx to json', err)
}