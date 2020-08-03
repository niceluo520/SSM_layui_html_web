
/** rsa 加密 */
function getRsaResult(vParam, empoent, module) {
    setMaxDigits(130); 
    var key = new RSAKeyPair(empoent, "", module);
    var result = encryptedString(key, encodeURIComponent(vParam)); 
    
    return result;
}