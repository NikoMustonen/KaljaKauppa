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
    i;

  if (test !== "" && test !== null && test !== undefined) {
    bits = str.toString().split(test);

    if (bits.length === 1) {
      if (bits[0] === test) {
        node.push(
          <mark>{test}</mark>
        );
      } else {
        node.push(bits);
      }
    } else {
      for (i = 0; i < bits.length - 1; i++) {
        node.push(bits[i]);
        node.push(
          <mark>{test}</mark>
        );
      }
      node.push(bits[bits.length - 1]);
    }

    return node;
  } else {
    return str;
  }
}

export {flatten, capitalizeFirstLetter, mark }
