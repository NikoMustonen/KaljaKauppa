import React from 'react';

function flatten(obj) {
  var root = {};
  (function tree(obj, index) {
    var suffix = toString.call(obj) === "[object Array]"
      ? "]"
      : "";
    for (var key in obj) {
      if (!obj.hasOwnProperty(key))
        continue;
      root[index + key + suffix] = obj[key];
      if (toString.call(obj[key]) === "[object Array]")
        tree(obj[key], index + key + suffix + "[");
      if (toString.call(obj[key]) === "[object Object]")
        tree(obj[key], index + key + suffix + ".");
      }
    })(obj, "");
  return root;
}

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1);
}

function mark(str, test) {
  var bits = [],
    node = [],
    i,
    key = 0;

  if (test !== "" && test !== null && test !== undefined) {
    bits = str.toString().split(test);

    if (bits.length === 1) {
      if (bits[0] === test) {
        key += 1;
        node.push(
          <mark key={key}>{test}</mark>
        );
      } else {
        node.push(bits);
      }
    } else {
      for (i = 0; i < bits.length - 1; i++) {
        key += 1;
        node.push(bits[i]);
        node.push(
          <mark key={key}>{test}</mark>
        );
      }
      node.push(bits[bits.length - 1]);
    }

    return node;
  } else {
    return str;
  }
}

function getImagefromDatabase(dburi) {
  return fetch(dburi)
  .then((response) => { return response.blob() })
  .then((blob) => { return URL.createObjectURL(blob) })
}

export {flatten, capitalizeFirstLetter, mark, getImagefromDatabase }
