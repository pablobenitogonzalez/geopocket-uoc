import Literals from "./Literals";

export default {
    getLiteralName(constant, key) {
        const element = Literals[constant].find(e => e.key === key);
        if (element !== undefined) {
            return element.name;
        }
        return '';
    },
    getLiteralShortName(constant, key) {
        const element = Literals[constant].find(e => e.key === key);
        if (element !== undefined) {
            return element.shortName;
        }
        return '';
    },
    getLiteralMetadata(constant, key) {
        const element = Literals[constant].find(e => e.key === key);
        if (element !== undefined) {
            return element.metadata;
        }
        return '';
    },
    getFormattedNumber(number, fractionDigits) {
        return (Math.round(number * Math.pow(10, fractionDigits)) / Math.pow(10, fractionDigits)).toFixed(fractionDigits);
    }
}