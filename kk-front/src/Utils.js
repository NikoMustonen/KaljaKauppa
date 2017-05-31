import React from 'react';

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


export { capitalizeFirstLetter, mark }
