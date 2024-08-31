import DimensionUtils from './Dimensions';

test('bytes2readable(): 0 B', () => {

    // arrange
    const value = 0;

    // act
    const result = DimensionUtils.bytes2readable(value);

    // assert
    expect(result).toBe("0 B")
});

test('bytes2readable(): 1 B', () => {

    // arrange
    const value = 1;

    // act
    const result = DimensionUtils.bytes2readable(value);

    // assert
    expect(result).toBe("1 B")
});

test('bytes2readable(): 1 KB', () => {

    // arrange
    const value = 1024;

    // act
    const result = DimensionUtils.bytes2readable(value);

    // assert
    expect(result).toBe("1 KB")
});

test('bytes2readable(): 1 MB', () => {

    // arrange
    const value = 1024 * 1024;

    // act
    const result = DimensionUtils.bytes2readable(value);

    // assert
    expect(result).toBe("1 MB")
});

test('bytes2readable(): 1 GB', () => {

    // arrange
    const value = 1024 * 1024 * 1024;

    // act
    const result = DimensionUtils.bytes2readable(value);

    // assert
    expect(result).toBe("1 GB")
});

test('bytes2readable(): 1 TB', () => {

    // arrange
    const value = 1024 * 1024 * 1024 * 1024;

    // act
    const result = DimensionUtils.bytes2readable(value);

    // assert
    expect(result).toBe("1 TB")
});

test('bytes2readable(): 512 MB', () => {

    // arrange
    const value = 512 * 1024;

    // act
    const result = DimensionUtils.bytes2readable(value);

    // assert
    expect(result).toBe("512 KB")
});

test('bytes2readable(): 223.9 MB', () => {

    // arrange
    const value = 234786482;

    // act
    const result = DimensionUtils.bytes2readable(value);

    // assert
    expect(result).toBe("223.9 MB")
});
